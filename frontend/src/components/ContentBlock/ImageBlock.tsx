import React from 'react';
import { ImageBlock } from '@/types';

interface ImageBlockProps {
  block: ImageBlock;
}

export const ImageBlockComponent: React.FC<ImageBlockProps> = ({ block }) => {
  return (
    <div className="mb-4">
      <img
        src={block.url}
        alt={block.alt}
        className="rounded-lg shadow-md max-w-full h-auto"
      />
      {block.caption && (
        <p className="text-sm text-gray-600 dark:text-gray-400 mt-2 text-center italic">
          {block.caption}
        </p>
      )}
    </div>
  );
};
