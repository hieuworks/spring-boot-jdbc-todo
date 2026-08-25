-- ==========================================================
-- 1. TABLES
-- ==========================================================

-- Todo categories
CREATE TABLE todo_categories (
                                 id BIGSERIAL PRIMARY KEY,
                                 name VARCHAR(255) NOT NULL,

                                 created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 deleted_at TIMESTAMPTZ
);


-- Todo
CREATE TABLE todo (
                      id BIGSERIAL PRIMARY KEY,

                      category_id BIGINT,

                      title VARCHAR(255) NOT NULL,
                      description TEXT,

                      status BOOLEAN NOT NULL DEFAULT FALSE,
                      current_version INTEGER NOT NULL DEFAULT 1,

                      created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      deleted_at TIMESTAMPTZ,

                      CONSTRAINT fk_todo_category
                          FOREIGN KEY (category_id)
                              REFERENCES todo_categories(id)
                              ON DELETE SET NULL,

                      CONSTRAINT chk_todo_current_version
                          CHECK (current_version >= 1)
);


-- Todo snapshot
CREATE TABLE todo_snapshot (
                               id BIGSERIAL PRIMARY KEY,

                               todo_id BIGINT NOT NULL,
                               category_id BIGINT,

                               title VARCHAR(255) NOT NULL,
                               description TEXT,

                               status BOOLEAN NOT NULL DEFAULT FALSE,
                               version INTEGER NOT NULL,

                               created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               deleted_at TIMESTAMPTZ,

                               CONSTRAINT fk_todo_snapshot_todo
                                   FOREIGN KEY (todo_id)
                                       REFERENCES todo(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_todo_snapshot_category
                                   FOREIGN KEY (category_id)
                                       REFERENCES todo_categories(id)
                                       ON DELETE SET NULL,

                               CONSTRAINT chk_todo_snapshot_version
                                   CHECK (version >= 1),

                               CONSTRAINT uq_todo_snapshot_version
                                   UNIQUE (todo_id, version)
);


-- ==========================================================
-- 2. INDEXES
-- ==========================================================

-- Find active todos by category
CREATE INDEX idx_todo_active_by_category
    ON todo(category_id)
    WHERE deleted_at IS NULL;


-- Find snapshot history newest first
CREATE INDEX idx_todo_snapshot_timeline
    ON todo_snapshot(todo_id, created_at DESC);


-- Find active categories
CREATE INDEX idx_todo_categories_active
    ON todo_categories(id)
    WHERE deleted_at IS NULL;


-- ==========================================================
-- 3. UPDATED_AT FUNCTION
-- ==========================================================

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER
AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- ==========================================================
-- 4. UPDATED_AT TRIGGERS
-- ==========================================================

CREATE TRIGGER trg_todo_categories_set_updated_at
    BEFORE UPDATE ON todo_categories
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();


CREATE TRIGGER trg_todo_set_updated_at
    BEFORE UPDATE ON todo
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();


CREATE TRIGGER trg_todo_snapshot_set_updated_at
    BEFORE UPDATE ON todo_snapshot
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();


-- ==========================================================
-- 5. TODO VERSION + SNAPSHOT FUNCTION
-- ==========================================================

CREATE OR REPLACE FUNCTION create_todo_snapshot()
RETURNS TRIGGER
AS $$
BEGIN

    -- Only create a new version when actual todo data changes
    IF OLD.title IS DISTINCT FROM NEW.title
       OR OLD.description IS DISTINCT FROM NEW.description
       OR OLD.status IS DISTINCT FROM NEW.status
       OR OLD.category_id IS DISTINCT FROM NEW.category_id
    THEN

        -- Increase todo version
        NEW.current_version = OLD.current_version + 1;

        -- Save the new state as a snapshot
INSERT INTO todo_snapshot (
    todo_id,
    category_id,
    title,
    description,
    status,
    version,
    created_at,
    updated_at
)
VALUES (
           NEW.id,
           NEW.category_id,
           NEW.title,
           NEW.description,
           NEW.status,
           NEW.current_version,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- ==========================================================
-- 6. TODO SNAPSHOT TRIGGER
-- ==========================================================

CREATE TRIGGER trg_todo_create_snapshot
    BEFORE UPDATE ON todo
    FOR EACH ROW
    EXECUTE FUNCTION create_todo_snapshot();