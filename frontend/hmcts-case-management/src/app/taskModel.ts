export interface Task {
  id: number;
  title: string;
  description: string | null;
  status: string;
  due: string;
}
