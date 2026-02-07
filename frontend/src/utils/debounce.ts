/**
 * Debounce utility function.
 * Demonstrates: Algorithm implementation for search optimization.
 * 
 * Interview talking point: "I implemented debouncing to minimize API calls
 * while typing in the search bar, improving performance and reducing server load."
 */

export function debounce<T extends (...args: any[]) => any>(
  func: T,
  delay: number
): (...args: Parameters<T>) => void {
  let timeoutId: ReturnType<typeof setTimeout>;

  return function (this: any, ...args: Parameters<T>) {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
      func.apply(this, args);
    }, delay);
  };
}
