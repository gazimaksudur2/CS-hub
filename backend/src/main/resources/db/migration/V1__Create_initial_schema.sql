-- Create Topic table with self-referencing relationship
CREATE TABLE topic (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    content TEXT,
    parent_id BIGINT,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_topic_parent FOREIGN KEY (parent_id) REFERENCES topic(id) ON DELETE CASCADE
);

-- Create index on parent_id for faster hierarchical queries
CREATE INDEX idx_topic_parent_id ON topic(parent_id);

-- Create index on slug for faster lookups
CREATE INDEX idx_topic_slug ON topic(slug);

-- Create Tag table
CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    color VARCHAR(7) DEFAULT '#3B82F6',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create junction table for Many-to-Many relationship (Topic <-> Tag)
CREATE TABLE topic_tags (
    topic_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (topic_id, tag_id),
    CONSTRAINT fk_topic_tags_topic FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE,
    CONSTRAINT fk_topic_tags_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_topic_tags_topic_id ON topic_tags(topic_id);
CREATE INDEX idx_topic_tags_tag_id ON topic_tags(tag_id);
