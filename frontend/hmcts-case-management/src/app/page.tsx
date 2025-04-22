"use client";

import { useEffect, useState } from "react";
import { getAllTasks } from "./api";

interface Task {
  id: number;
  title: string;
  description: string | null;
  status: string;
}

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>();
  const [error, setError] = useState<any>();

  useEffect(() => {
    getAllTasks()
      .then((data: any) => setTasks(data))
      .catch((err) => setError(err));
  }, []);

  return (
    <>
      <h1 className="m-4 font-serif text-3xl">Tasks</h1>
      <div className="flex flex-row-reverse">
        <button
          className="mr-4 font-serif text-xl p-1 border rounded-[7px]"
          onClick={() => console.log("task created")}
        >
          Add new task
        </button>
      </div>
      {tasks?.map((task) => {
        return (
          <div
            className="border rounded-[10px] mt-5 relative m-5 max-w-150 bg-[#fff9f9]"
            key={task.id}
          >
            <p className="text-2xl bg-[#7a93de] font-serif p-1" data-testid="task-title">{task.title}</p>
            <p className="text-xl mt-1 font-serif p-1 mb-5">
              {task.description ? task.description : "No description provided"}
            </p>
            <div className="absolute bottom-0 text-xl font-serif p-1 mt-7">
              Status: {task.status}
            </div>
          </div>
        );
      })}
    </>
  );
}
