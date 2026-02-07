import React from 'react';
import { ContentBlock as ContentBlockType } from '@/types';
import { TextBlockComponent } from './TextBlock';
import { CodeBlockComponent } from './CodeBlock';
import { ImageBlockComponent } from './ImageBlock';

/**
 * ContentBlock component demonstrating OOP concept: Polymorphism.
 * Factory pattern - returns different components based on block type.
 * 
 * Interview talking point: "I implemented a polymorphic content block system
 * using TypeScript union types and the Factory pattern, allowing different
 * content types to be rendered with a single interface."
 */

interface ContentBlockProps {
  block: ContentBlockType;
}

export const ContentBlock: React.FC<ContentBlockProps> = ({ block }) => {
  // Factory pattern: Return different components based on type
  switch (block.type) {
    case 'text':
      return <TextBlockComponent block={block} />;
    case 'code':
      return <CodeBlockComponent block={block} />;
    case 'image':
      return <ImageBlockComponent block={block} />;
    default:
      // TypeScript exhaustiveness check
      const _exhaustive: never = block;
      return null;
  }
};
