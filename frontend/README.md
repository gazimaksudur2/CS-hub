# CSHub Frontend

React + TypeScript frontend for the CSHub documentation platform.

## 🏗️ Architecture

### Component Structure

```
App
└── MainLayout
    ├── Sidebar (Recursive tree navigation)
    ├── Header (Search + Dark mode)
    └── Content
        ├── HomePage
        └── TopicPage
            └── MarkdownViewer
                └── SyntaxHighlighter
```

### State Management

Uses **Zustand** for global state:

```typescript
interface AppState {
  topics: Topic[];
  currentTopic: Topic | null;
  isLoading: boolean;
  darkMode: boolean;
  
  fetchTopicTree: () => Promise<void>;
  fetchTopicBySlug: (slug: string) => Promise<void>;
  searchTopics: (keyword: string) => Promise<void>;
}
```

## 🚀 Quick Start

### Prerequisites

- Node.js 18+
- npm or yarn

### Setup

1. Install dependencies:
```bash
npm install
```

2. Create environment file:
```bash
cp .env.example .env
```

3. Start development server:
```bash
npm run dev
```

4. Build for production:
```bash
npm run build
```

## 🎨 Styling

Uses **Tailwind CSS** with custom configuration:

- Dark mode support
- Custom color palette
- Responsive design
- Typography plugin for markdown

### Dark Mode

Dark mode state is managed by Zustand and persisted in the DOM:

```typescript
toggleDarkMode: () => set((state) => {
  const newDarkMode = !state.darkMode;
  if (newDarkMode) {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
  return { darkMode: newDarkMode };
}),
```

## 🧩 Key Components

### 1. Recursive Sidebar

**Location:** `src/components/Sidebar/SidebarItem.tsx`

Demonstrates recursion in React:

```tsx
export const SidebarItem: React.FC<SidebarItemProps> = ({ topic, level = 0 }) => {
  return (
    <div>
      <div onClick={handleClick}>{topic.title}</div>
      {hasChildren && isExpanded && (
        <div>
          {topic.children.map((child) => (
            <SidebarItem topic={child} level={level + 1} />
          ))}
        </div>
      )}
    </div>
  );
};
```

### 2. Polymorphic Content Blocks

**Location:** `src/components/ContentBlock/`

Factory pattern implementation:

```tsx
export const ContentBlock: React.FC<ContentBlockProps> = ({ block }) => {
  switch (block.type) {
    case 'text': return <TextBlockComponent block={block} />;
    case 'code': return <CodeBlockComponent block={block} />;
    case 'image': return <ImageBlockComponent block={block} />;
  }
};
```

### 3. Debounced Search

**Location:** `src/components/SearchBar/`

Optimizes API calls:

```typescript
const debouncedSearch = useCallback(
  debounce(async (keyword: string) => {
    await searchTopics(keyword);
  }, 300), // 300ms delay
  []
);
```

### 4. Markdown Viewer

**Location:** `src/components/MarkdownViewer/`

Renders markdown with syntax highlighting:

```tsx
<ReactMarkdown
  remarkPlugins={[remarkGfm]}
  components={{
    code({ inline, className, children }) {
      return !inline ? (
        <SyntaxHighlighter language={language}>
          {children}
        </SyntaxHighlighter>
      ) : (
        <code>{children}</code>
      );
    },
  }}
>
  {content}
</ReactMarkdown>
```

## 📱 Responsive Design

- Mobile-first approach
- Sidebar collapses on mobile
- Touch-friendly navigation
- Optimized for all screen sizes

### Breakpoints

```javascript
// Tailwind breakpoints
sm: '640px'   // Mobile landscape
md: '768px'   // Tablet
lg: '1024px'  // Desktop
xl: '1280px'  // Large desktop
```

## 🎯 Type Safety

Uses **TypeScript in strict mode**:

```typescript
// All API responses are strongly typed
interface Topic {
  id: number;
  title: string;
  slug: string;
  content: string;
  children: Topic[];
  tags: Tag[];
}

// Union types for polymorphism
type ContentBlock = TextBlock | CodeBlock | ImageBlock;
```

## 🔧 Configuration

### Vite Config

```typescript
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 3000,
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
});
```

### Tailwind Config

```javascript
module.exports = {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: { /* custom blue palette */ },
      },
    },
  },
};
```

## 📦 Dependencies

### Production
- react: ^18.2.0
- react-router-dom: ^6.20.0
- zustand: ^4.4.7
- axios: ^1.6.2
- react-markdown: ^9.0.1
- react-syntax-highlighter: ^15.5.0
- lucide-react: ^0.294.0

### Development
- vite: ^5.0.8
- typescript: ^5.3.3
- tailwindcss: ^3.3.6
- @types/react: ^18.2.43

## 🧪 Best Practices

### 1. Component Organization

```
Component/
├── index.tsx          # Main component
├── SubComponent.tsx   # Sub-components
└── types.ts          # Component-specific types
```

### 2. Custom Hooks

Keep logic reusable:

```typescript
// Could add custom hooks like:
const useDebounce = (value, delay) => { ... };
const useLocalStorage = (key, initial) => { ... };
```

### 3. Error Boundaries

Could be added for better error handling:

```typescript
class ErrorBoundary extends React.Component { ... }
```

## 🚀 Performance Optimizations

1. **Code Splitting**: React Router lazy loading
2. **Debouncing**: Search API calls
3. **Memoization**: Could add React.memo for expensive components
4. **Virtual Scrolling**: Could add for large topic lists
5. **Image Optimization**: Could add lazy loading

## 🎨 Theming

### Light Mode
- Clean, professional design
- High contrast for readability
- Subtle shadows

### Dark Mode
- Easy on the eyes
- Consistent with modern UI trends
- Proper contrast ratios

## 📝 Future Improvements

1. **Offline Support**: Service workers and caching
2. **Accessibility**: ARIA labels, keyboard navigation
3. **Internationalization**: i18n support
4. **Analytics**: Track user behavior
5. **PWA**: Progressive Web App features

## 🤝 Contributing

When contributing:
1. Follow TypeScript strict mode
2. Use Tailwind for styling (no custom CSS unless necessary)
3. Keep components small and focused
4. Write self-documenting code
5. Use meaningful variable names
