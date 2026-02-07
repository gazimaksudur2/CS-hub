import { create } from 'zustand';
import { Topic, Tag } from '@/types';
import { apiClient } from '@/api/client';

/**
 * Zustand store for global state management.
 * Demonstrates: State management without Redux boilerplate.
 */

interface AppState {
  // State
  topics: Topic[];
  currentTopic: Topic | null;
  tags: Tag[];
  isLoading: boolean;
  error: string | null;
  sidebarOpen: boolean;
  darkMode: boolean;

  // Actions
  fetchTopicTree: () => Promise<void>;
  fetchTopicBySlug: (slug: string) => Promise<void>;
  searchTopics: (keyword: string) => Promise<void>;
  fetchTags: () => Promise<void>;
  setCurrentTopic: (topic: Topic | null) => void;
  toggleSidebar: () => void;
  toggleDarkMode: () => void;
  setError: (error: string | null) => void;
}

export const useStore = create<AppState>((set) => ({
  // Initial state
  topics: [],
  currentTopic: null,
  tags: [],
  isLoading: false,
  error: null,
  sidebarOpen: true,
  darkMode: false,

  // Fetch topic tree for sidebar navigation
  fetchTopicTree: async () => {
    set({ isLoading: true, error: null });
    try {
      const topics = await apiClient.getTopicTree();
      set({ topics, isLoading: false });
    } catch (error) {
      set({ 
        error: 'Failed to load topics. Please try again later.', 
        isLoading: false 
      });
      console.error('Error fetching topic tree:', error);
    }
  },

  // Fetch a single topic by slug
  fetchTopicBySlug: async (slug: string) => {
    set({ isLoading: true, error: null });
    try {
      const topic = await apiClient.getTopicBySlug(slug);
      set({ currentTopic: topic, isLoading: false });
    } catch (error) {
      set({ 
        error: 'Topic not found.', 
        currentTopic: null,
        isLoading: false 
      });
      console.error('Error fetching topic:', error);
    }
  },

  // Search topics
  searchTopics: async (keyword: string) => {
    if (!keyword.trim()) {
      set({ topics: [] });
      return;
    }

    set({ isLoading: true, error: null });
    try {
      const topics = await apiClient.searchTopics(keyword);
      set({ topics, isLoading: false });
    } catch (error) {
      set({ 
        error: 'Search failed. Please try again.', 
        isLoading: false 
      });
      console.error('Error searching topics:', error);
    }
  },

  // Fetch all tags
  fetchTags: async () => {
    try {
      const tags = await apiClient.getAllTags();
      set({ tags });
    } catch (error) {
      console.error('Error fetching tags:', error);
    }
  },

  // Set current topic
  setCurrentTopic: (topic) => set({ currentTopic: topic }),

  // Toggle sidebar
  toggleSidebar: () => set((state) => ({ sidebarOpen: !state.sidebarOpen })),

  // Toggle dark mode
  toggleDarkMode: () => set((state) => {
    const newDarkMode = !state.darkMode;
    if (newDarkMode) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
    return { darkMode: newDarkMode };
  }),

  // Set error
  setError: (error) => set({ error }),
}));
