import React, { useState } from 'react';
import { ChevronRight, ChevronDown } from 'lucide-react';
import { Topic } from '@/types';
import { useNavigate, useLocation } from 'react-router-dom';

/**
 * Recursive SidebarItem component.
 * Demonstrates DSA concept: Recursion in UI rendering for tree structure.
 * 
 * Interview talking point: "I implemented a recursive React component
 * that renders itself for child nodes, mirroring the tree data structure
 * from the backend."
 */

interface SidebarItemProps {
  topic: Topic;
  level?: number;
}

export const SidebarItem: React.FC<SidebarItemProps> = ({ topic, level = 0 }) => {
  const [isExpanded, setIsExpanded] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  const hasChildren = topic.children && topic.children.length > 0;
  const isActive = location.pathname === `/topic/${topic.slug}`;

  const handleClick = () => {
    if (hasChildren) {
      setIsExpanded(!isExpanded);
    }
    navigate(`/topic/${topic.slug}`);
  };

  return (
    <div className="select-none">
      <div
        onClick={handleClick}
        className={`
          flex items-center gap-2 px-3 py-2 rounded-md cursor-pointer
          transition-colors duration-150
          ${isActive 
            ? 'bg-primary-100 dark:bg-primary-900 text-primary-700 dark:text-primary-300 font-medium' 
            : 'hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-700 dark:text-gray-300'
          }
        `}
        style={{ paddingLeft: `${level * 16 + 12}px` }}
      >
        {hasChildren && (
          <span className="flex-shrink-0">
            {isExpanded ? (
              <ChevronDown className="w-4 h-4" />
            ) : (
              <ChevronRight className="w-4 h-4" />
            )}
          </span>
        )}
        {!hasChildren && <span className="w-4" />}
        <span className="flex-1 truncate">{topic.title}</span>
      </div>

      {/* Recursive rendering of children */}
      {hasChildren && isExpanded && (
        <div className="mt-1">
          {topic.children.map((child) => (
            <SidebarItem 
              key={child.id} 
              topic={child} 
              level={level + 1}  // Increase nesting level
            />
          ))}
        </div>
      )}
    </div>
  );
};
