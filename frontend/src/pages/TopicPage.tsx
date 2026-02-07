import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useStore } from '@/store/useStore';
import { MarkdownViewer } from '@/components/MarkdownViewer';
import { Calendar, Tag as TagIcon } from 'lucide-react';

/**
 * Topic detail page component.
 */

export const TopicPage: React.FC = () => {
  const { slug } = useParams<{ slug: string }>();
  const { currentTopic, isLoading, error, fetchTopicBySlug } = useStore();

  useEffect(() => {
    if (slug) {
      fetchTopicBySlug(slug);
    }
  }, [slug, fetchTopicBySlug]);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-[400px]">
        <div className="text-center space-y-3">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto" />
          <p className="text-gray-600 dark:text-gray-400">Loading topic...</p>
        </div>
      </div>
    );
  }

  if (error || !currentTopic) {
    return (
      <div className="flex items-center justify-center min-h-[400px]">
        <div className="text-center space-y-3">
          <p className="text-xl text-red-600 dark:text-red-400">
            {error || 'Topic not found'}
          </p>
          <p className="text-gray-600 dark:text-gray-400">
            Please try selecting a different topic from the sidebar.
          </p>
        </div>
      </div>
    );
  }

  return (
    <article className="space-y-6">
      {/* Header */}
      <header className="space-y-4 pb-6 border-b border-gray-200 dark:border-gray-700">
        <h1 className="text-4xl font-bold text-gray-900 dark:text-gray-100">
          {currentTopic.title}
        </h1>

        {/* Meta Information */}
        <div className="flex flex-wrap items-center gap-4 text-sm text-gray-600 dark:text-gray-400">
          <div className="flex items-center gap-2">
            <Calendar className="w-4 h-4" />
            <span>
              Updated: {new Date(currentTopic.updatedAt).toLocaleDateString()}
            </span>
          </div>

          {currentTopic.tags && currentTopic.tags.length > 0 && (
            <div className="flex items-center gap-2">
              <TagIcon className="w-4 h-4" />
              <div className="flex flex-wrap gap-2">
                {currentTopic.tags.map((tag) => (
                  <span
                    key={tag.id}
                    className="px-2 py-1 rounded text-xs font-medium"
                    style={{
                      backgroundColor: tag.color + '20',
                      color: tag.color,
                    }}
                  >
                    {tag.name}
                  </span>
                ))}
              </div>
            </div>
          )}
        </div>
      </header>

      {/* Content */}
      <div className="prose prose-slate dark:prose-invert max-w-none">
        {currentTopic.content ? (
          <MarkdownViewer content={currentTopic.content} />
        ) : (
          <p className="text-gray-600 dark:text-gray-400">
            No content available for this topic yet.
          </p>
        )}
      </div>
    </article>
  );
};
