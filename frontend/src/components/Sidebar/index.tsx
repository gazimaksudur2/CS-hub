import React, { useEffect } from 'react';
import { SidebarItem } from './SidebarItem';
import { useStore } from '@/store/useStore';
import { X } from 'lucide-react';

/**
 * Sidebar component displaying the topic tree navigation.
 */

export const Sidebar: React.FC = () => {
  const { topics, isLoading, fetchTopicTree, sidebarOpen, toggleSidebar } = useStore();

  useEffect(() => {
    if (topics.length === 0) {
      fetchTopicTree();
    }
  }, [topics, fetchTopicTree]);

  return (
    <>
      {/* Overlay for mobile */}
      {sidebarOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-50 z-40 lg:hidden"
          onClick={toggleSidebar}
        />
      )}

      {/* Sidebar */}
      <aside
        className={`
          fixed lg:sticky top-0 left-0 h-screen w-64 bg-white dark:bg-gray-900 
          border-r border-gray-200 dark:border-gray-700 
          overflow-y-auto z-50 transition-transform duration-300
          ${sidebarOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'}
        `}
      >
        <div className="p-4">
          {/* Header */}
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-xl font-bold text-gray-900 dark:text-gray-100">
              AlgoWiki
            </h2>
            <button
              onClick={toggleSidebar}
              className="lg:hidden text-gray-500 hover:text-gray-700"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          {/* Navigation */}
          {isLoading ? (
            <div className="text-center py-8 text-gray-500">Loading...</div>
          ) : (
            <nav className="space-y-1">
              {topics.map((topic) => (
                <SidebarItem key={topic.id} topic={topic} />
              ))}
            </nav>
          )}
        </div>
      </aside>
    </>
  );
};
