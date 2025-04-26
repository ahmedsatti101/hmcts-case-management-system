"use client";

import { getSingleTask, updateTaskStatus } from "@/app/api";
import { Task } from "@/app/taskModel";
import { AxiosError } from "axios";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useState, useEffect } from "react";
import Modal from "./modal";

export default function Page() {
  const { id } = useParams<{ id: string }>();
  const [task, setTask] = useState<Task>();
  const [error, setError] = useState<AxiosError>();
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const dueDateTime = new Date(task?.due);

  useEffect(() => {
    if (id) {
      getSingleTask(id)
        .then((data: Task) => setTask(data))
        .catch((err) => setError(err))
        .finally(() => setLoading(false));
    }
  }, [id]);

  const openModal = () => {
    setOpen(true); 
  };

  if (loading) return <p className="m-auto p-[10px] text-center">Loading...</p>;

  if (error)
    return <p className="m-auto p-[10px] text-center">{error.message}</p>;

  return (
    <>
      <div className="flex flex-row">
        <Link
          className="m-4 font-semibold text-lg p-1 border rounded-[7px]"
          href="/"
          data-testid="back-home-button"
        >
          Back to home
        </Link>
      </div>
      <div className="border rounded-[10px] mt-5 relative m-5 max-w-150 bg-[#fff9f9]">
        <p
          className="text-2xl bg-[#7a93de] font-serif p-1"
          data-testid="single-task-title"
        >
          {task?.title}
        </p>
        <p className="font-serif text-2xl pl-1">Description</p>
        <p
          className="text-xl font-serif pl-1 pb-1"
          data-testid="single-task-description"
        >
          {task?.description ? task.description : "No description provided"}
        </p>
        <div className="text-xl font-serif p-1" data-testid="single-task-status">
          Status: {" "}
          <select
            value={task?.status || ""}
            onChange={(e) => {
              if (task && id) {
                updateTaskStatus(id, e.target.value)
                  .then(updatedTask => setTask(updatedTask))
                  .catch(err => console.error(err));
              }
            }}
            className="ml-2 text-lg border rounded-md p-1">
            <option value="Todo">Todo</option>
            <option value="Complete">Complete</option>
            <option value="In progress">In progress</option>
          </select>
        </div>
        <p className="text-xl font-serif p-1" data-testid="single-task-duedate">
          Due by: {dueDateTime.toISOString().substring(0, 10)}{" "}
          {dueDateTime.toISOString().substring(11, 16)}
        </p>
      </div>
      <div className="flex flex-row-reverse">
        <button className="border rounded-[7px] font-semibold text-lg p-1 mr-2 bg-red-600 text-white" onClick={openModal}>
          Delete task
        </button>
      </div>
      <Modal open={open} setOpenAction={setOpen} taskId={id} setErrorAction={setError}/>
    </>
  );
}
