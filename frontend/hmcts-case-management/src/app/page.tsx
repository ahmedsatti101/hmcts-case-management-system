"use client";

import { useEffect, useState } from "react";
import { getAllTasks } from "./api";
import { AxiosError } from "axios";
import { Task } from "./taskModel";
import Link from "next/link";
import Modal from "./modal";

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>();
  const [error, setError] = useState<AxiosError>();
  const [loading, setLoading] = useState<boolean>(true);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    getAllTasks()
      .then((data: Task[]) => setTasks(data))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, []);

  const handleAddingTask = () => {
    setOpen(true);
  }

  if (loading) return <p className="m-auto p-[10px] text-center">Loading...</p>;

  if (error) return <p className="m-auto p-[10px] text-center">{error.message}</p>;

  return (
    <>
      <Modal open={open} setOpenAction={setOpen}/>
      <h1 className="m-4 font-serif text-3xl">Tasks</h1>
      <div className="flex flex-row-reverse">
        <button
          className="mr-4 font-semibold text-gray-900 text-lg p-1 border rounded-[7px]"
          onClick={handleAddingTask}
        >
          Add new task
        </button>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2">
        {tasks?.length ? (tasks?.map((task) => {
          return (
            <Link href={`/task/${task.id}`} key={task.id}>
              <div
                className="border rounded-[10px] mt-5 relative m-5 max-w-150 bg-[#fff9f9]"
              >
                <p className="text-2xl bg-[#7a93de] font-serif p-1" data-testid="task-title">{task.title}</p>
                <p className="text-xl mt-1 font-serif p-1 mb-5" data-testid="task-description">
                  {task.description ? task.description : "No description provided"}
                </p>
                <div className="text-xl font-serif p-1 mt-7" data-testid="task-status">
                  Status: {task.status}
                </div>
              </div>
            </Link>
          );
        })) : (
          <p className="text-center p-4 text-gray-500">
            No tasks found. Click "Add new task" to create a task.
          </p>
        )}
      </div>
    </>
  );
}
