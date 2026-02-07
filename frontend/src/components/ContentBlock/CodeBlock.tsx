import React from 'react';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { vscDarkPlus, vs } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { CodeBlock } from '@/types';
import { useStore } from '@/store/useStore';

interface CodeBlockProps {
  block: CodeBlock;
}

export const CodeBlockComponent: React.FC<CodeBlockProps> = ({ block }) => {
  const darkMode = useStore((state) => state.darkMode);

  return (
    <div className="mb-4 rounded-lg overflow-hidden">
      <div className="bg-gray-800 dark:bg-gray-900 px-4 py-2 flex justify-between items-center">
        <span className="text-sm text-gray-300 font-mono">{block.language}</span>
      </div>
      <SyntaxHighlighter
        language={block.language}
        style={darkMode ? vscDarkPlus : vs}
        customStyle={{
          margin: 0,
          borderRadius: 0,
          fontSize: '0.875rem',
        }}
        showLineNumbers
      >
        {block.code}
      </SyntaxHighlighter>
    </div>
  );
};
