CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email TEXT NOT NULL 
        CONSTRAINT users_email_unique UNIQUE 
        CONSTRAINT users_email_lower_check CHECK (email = LOWER(email)),
    username TEXT NOT NULL 
        CONSTRAINT users_username_format_check CHECK (username ~ '^[a-z0-9_]{3,30}$') 
        CONSTRAINT users_username_unique UNIQUE,
    password_hash TEXT,
    role TEXT DEFAULT 'USER' NOT NULL 
        CONSTRAINT users_role_check CHECK (role IN ('ADMIN', 'USER')),
    email_verified_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()

);