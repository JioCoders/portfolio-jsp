-- changeset jio:003-create-vector-ext:1
CREATE EXTENSION IF NOT EXISTS vector;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
    id uuid NOT NULL DEFAULT uuid_generate_v4 (),
    content text,
    metadata json,
    embedding vector (768),
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS vector_store_embedding_idx ON vector_store USING hnsw (embedding vector_cosine_ops);