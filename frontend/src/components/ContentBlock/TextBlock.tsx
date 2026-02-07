import React from 'react';
import { TextBlock } from '@/types';

interface TextBlockProps {
  block: TextBlock;
}

export const TextBlockComponent: React.FC<TextBlockProps> = ({ block }) => {
  return (
    <div className="mb-4">
      <p className="text-gray-700 dark:text-gray-300 leading-relaxed">
        {block.content}
      </p>
    </div>
  );
};
