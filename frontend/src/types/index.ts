/**
 * Type definitions for AlgoWiki application.
 * Demonstrates: TypeScript strict typing for type safety.
 */

export interface Topic {
  id: number;
  title: string;
  slug: string;
  content: string;
  parentId: number | null;
  displayOrder: number;
  children: Topic[];
  tags: Tag[];
  createdAt: string;
  updatedAt: string;
}

export interface Tag {
  id: number;
  name: string;
  color: string;
}

export interface CreateTopicRequest {
  title: string;
  slug: string;
  content: string;
  parentId?: number | null;
  displayOrder?: number;
  tagIds?: number[];
}

export interface ErrorResponse {
  status: number;
  message: string;
  path: string;
  timestamp: string;
  errors?: Record<string, string>;
}

/**
 * Content Block types for polymorphic content system.
 * Demonstrates OOP concept: Polymorphism through union types.
 */
export type ContentBlockType = 'text' | 'code' | 'image';

export interface BaseContentBlock {
  id: string;
  type: ContentBlockType;
}

export interface TextBlock extends BaseContentBlock {
  type: 'text';
  content: string;
}

export interface CodeBlock extends BaseContentBlock {
  type: 'code';
  language: string;
  code: string;
}

export interface ImageBlock extends BaseContentBlock {
  type: 'image';
  url: string;
  alt: string;
  caption?: string;
}

export type ContentBlock = TextBlock | CodeBlock | ImageBlock;
