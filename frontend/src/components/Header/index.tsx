import React from 'react';
import { Menu, Moon, Sun, Github } from 'lucide-react';
import { useStore } from '@/store/useStore';
import { SearchBar } from '@/components/SearchBar';

/**
 * Header component with navigation and controls.
 */

export const Header: React.FC = () => {
  const { darkMode, toggleDarkMode, toggleSidebar } = useStore();

  return (
    <header className="sticky top-0 z-30 bg-white dark:bg-gray-900 border-b border-gray-200 dark:border-gray-700 shadow-sm">
      <div className="px-4 py-3">
        <div className="flex items-center gap-4">
          {/* Menu Button (Mobile) */}
          <button
            onClick={toggleSidebar}
            className="lg:hidden text-gray-700 dark:text-gray-300 hover:text-gray-900 dark:hover:text-gray-100"
          >
            <Menu className="w-6 h-6" />
          </button>

          {/* Logo (Desktop) */}
          <div className="hidden lg:block">
            <h1 className="text-xl font-bold text-gray-900 dark:text-gray-100">
              CS Knowledge Hub
            </h1>
          </div>

          {/* Search Bar */}
          <div className="flex-1 max-w-2xl">
            <SearchBar />
          </div>

          {/* Actions */}
          <div className="flex items-center gap-2">
            {/* Dark Mode Toggle */}
            <button
              onClick={toggleDarkMode}
              className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-700 dark:text-gray-300"
              aria-label="Toggle dark mode"
            >
              {darkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
            </button>

            {/* GitHub Link */}
            <a
              href="https://github.com"
              target="_blank"
              rel="noopener noreferrer"
              className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-700 dark:text-gray-300"
              aria-label="GitHub repository"
            >
              <Github className="w-5 h-5" />
            </a>
          </div>
        </div>
      </div>
    </header>
  );
};
