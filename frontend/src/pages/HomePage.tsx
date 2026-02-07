import React from 'react';
import { BookOpen, Code2, Database, Zap } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

/**
 * Home page component.
 */

export const HomePage: React.FC = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: <Code2 className="w-8 h-8" />,
      title: 'Data Structures & Algorithms',
      description: 'Master fundamental data structures and algorithmic techniques.',
      slug: 'dsa',
    },
    {
      icon: <BookOpen className="w-8 h-8" />,
      title: 'Object-Oriented Programming',
      description: 'Learn OOP principles and design patterns.',
      slug: 'oop',
    },
    {
      icon: <Database className="w-8 h-8" />,
      title: 'Database Management',
      description: 'Understand database design, SQL, and optimization.',
      slug: 'dbms',
    },
  ];

  return (
    <div className="space-y-12">
      {/* Hero Section */}
      <section className="text-center space-y-4">
        <h1 className="text-5xl font-bold text-gray-900 dark:text-gray-100">
          Welcome to AlgoWiki
        </h1>
        <p className="text-xl text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
          Your comprehensive guide to Computer Science fundamentals.
          Master DSA, OOP, and DBMS concepts with clear explanations and examples.
        </p>
      </section>

      {/* Features Grid */}
      <section className="grid md:grid-cols-3 gap-6">
        {features.map((feature) => (
          <div
            key={feature.slug}
            onClick={() => navigate(`/topic/${feature.slug}`)}
            className="p-6 bg-white dark:bg-gray-800 rounded-lg shadow-md border border-gray-200 dark:border-gray-700 
                     hover:shadow-lg hover:border-primary-500 transition-all cursor-pointer"
          >
            <div className="text-primary-600 dark:text-primary-400 mb-4">
              {feature.icon}
            </div>
            <h3 className="text-xl font-semibold text-gray-900 dark:text-gray-100 mb-2">
              {feature.title}
            </h3>
            <p className="text-gray-600 dark:text-gray-400">
              {feature.description}
            </p>
          </div>
        ))}
      </section>

      {/* Features List */}
      <section className="bg-gradient-to-r from-primary-50 to-blue-50 dark:from-gray-800 dark:to-gray-900 
                        rounded-lg p-8 space-y-4">
        <h2 className="text-3xl font-bold text-gray-900 dark:text-gray-100 mb-6">
          Why AlgoWiki?
        </h2>
        <div className="grid md:grid-cols-2 gap-4">
          <div className="flex items-start gap-3">
            <Zap className="w-6 h-6 text-primary-600 flex-shrink-0 mt-1" />
            <div>
              <h3 className="font-semibold text-gray-900 dark:text-gray-100">
                Interview-Ready Content
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Focused on concepts commonly asked in technical interviews.
              </p>
            </div>
          </div>
          <div className="flex items-start gap-3">
            <Code2 className="w-6 h-6 text-primary-600 flex-shrink-0 mt-1" />
            <div>
              <h3 className="font-semibold text-gray-900 dark:text-gray-100">
                Code Examples
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Learn with practical Java code examples and implementations.
              </p>
            </div>
          </div>
          <div className="flex items-start gap-3">
            <BookOpen className="w-6 h-6 text-primary-600 flex-shrink-0 mt-1" />
            <div>
              <h3 className="font-semibold text-gray-900 dark:text-gray-100">
                Structured Learning
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Topics organized in a hierarchical structure for easy navigation.
              </p>
            </div>
          </div>
          <div className="flex items-start gap-3">
            <Database className="w-6 h-6 text-primary-600 flex-shrink-0 mt-1" />
            <div>
              <h3 className="font-semibold text-gray-900 dark:text-gray-100">
                Real-World Applications
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Understand how concepts apply to production systems.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Call to Action */}
      <section className="text-center space-y-4">
        <h2 className="text-2xl font-bold text-gray-900 dark:text-gray-100">
          Ready to Start Learning?
        </h2>
        <p className="text-gray-600 dark:text-gray-400">
          Navigate through topics using the sidebar or search for specific concepts.
        </p>
      </section>
    </div>
  );
};
