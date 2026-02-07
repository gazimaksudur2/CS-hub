import axios, { AxiosInstance, AxiosError } from 'axios';
import { Topic, Tag, CreateTopicRequest, ErrorResponse } from '@/types';

/**
 * API Client for communicating with the backend.
 * Demonstrates: Separation of concerns, centralized API logic.
 */

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

class ApiClient {
  private client: AxiosInstance;

  constructor() {
    this.client = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        'Content-Type': 'application/json',
      },
      timeout: 10000,
    });

    // Response interceptor for error handling
    this.client.interceptors.response.use(
      (response) => response,
      (error: AxiosError<ErrorResponse>) => {
        if (error.response) {
          console.error('API Error:', error.response.data);
        } else if (error.request) {
          console.error('Network Error:', error.message);
        }
        return Promise.reject(error);
      }
    );
  }

  // Topic endpoints
  async getTopicTree(): Promise<Topic[]> {
    const response = await this.client.get<Topic[]>('/topics/tree');
    return response.data;
  }

  async getAllTopics(): Promise<Topic[]> {
    const response = await this.client.get<Topic[]>('/topics');
    return response.data;
  }

  async getTopicById(id: number): Promise<Topic> {
    const response = await this.client.get<Topic>(`/topics/${id}`);
    return response.data;
  }

  async getTopicBySlug(slug: string): Promise<Topic> {
    const response = await this.client.get<Topic>(`/topics/slug/${slug}`);
    return response.data;
  }

  async searchTopics(keyword: string): Promise<Topic[]> {
    const response = await this.client.get<Topic[]>('/topics/search', {
      params: { keyword },
    });
    return response.data;
  }

  async getTopicsByTag(tagName: string): Promise<Topic[]> {
    const response = await this.client.get<Topic[]>(`/topics/tag/${tagName}`);
    return response.data;
  }

  async createTopic(request: CreateTopicRequest): Promise<Topic> {
    const response = await this.client.post<Topic>('/topics', request);
    return response.data;
  }

  async updateTopic(id: number, request: CreateTopicRequest): Promise<Topic> {
    const response = await this.client.put<Topic>(`/topics/${id}`, request);
    return response.data;
  }

  async deleteTopic(id: number): Promise<void> {
    await this.client.delete(`/topics/${id}`);
  }

  // Tag endpoints
  async getAllTags(): Promise<Tag[]> {
    const response = await this.client.get<Tag[]>('/tags');
    return response.data;
  }

  async getTagById(id: number): Promise<Tag> {
    const response = await this.client.get<Tag>(`/tags/${id}`);
    return response.data;
  }

  async createTag(name: string, color?: string): Promise<Tag> {
    const response = await this.client.post<Tag>('/tags', null, {
      params: { name, color },
    });
    return response.data;
  }

  async deleteTag(id: number): Promise<void> {
    await this.client.delete(`/tags/${id}`);
  }
}

export const apiClient = new ApiClient();
