import { Dialog, DialogBackdrop, DialogPanel } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/16/solid";
import { AxiosError } from "axios";
import { Dispatch, SetStateAction, useState } from "react";
import { createTask } from "./api";
import { format, parseISO } from "date-fns";

type Props = {
  open: boolean;
  setOpenAction: Dispatch<SetStateAction<boolean>>;
  setErrorAction?: Dispatch<
    SetStateAction<AxiosError<unknown, any> | undefined>
  >;
};

export default function Modal({ open, setOpenAction }: Props) {
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string | null>(null);
  const [status, setStatus] = useState<string>("Todo");
  const [dueBy, setDueBy] = useState<string>(
    format(new Date(), "yyyy-MM-dd'T'HH:mm:ss"),
  );

  const handleSubmit = () => {
    const formattedDueBy = format(parseISO(dueBy), "yyyy-MM-dd'T'HH:mm:ss");

    createTask(title, description, status, formattedDueBy);
  };
  return (
    <Dialog open={open} onClose={setOpenAction} data-testid="create-task-modal">
      <DialogBackdrop
        transition
        className="fixed inset-0 bg-gray-500/75 transition-opacity data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in"
      />
      <DialogPanel
        transition
        className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-full max-w-[calc(100vw-2rem)] mx-1 overflow-hidden
        rounded-lg bg-white text-left shadow-xl transition-all data-closed:translate-y-4 data-closed:opacity-0 data-enter:duration-300
        data-enter:ease-out data-leave:duration-200 data-leave:ease-in sm:my-8 sm:max-w-lg"
      >
        <form>
          <div>
            <h2 className="text-3xl m-1">Create task</h2>

            <div className="m-1">
              <label htmlFor="title" className="text-xl">
                Title
              </label>
              <div className="mb-4">
                <input
                  id="title"
                  name="title"
                  type="text"
                  placeholder="Task title"
                  className="border mt-2 rounded-md"
                  autoComplete="off"
                  required
                  onChange={(e) => setTitle(e.target.value)}
                />
              </div>
              <label htmlFor="description" className="text-xl">
                Description (optional)
              </label>
              <div className="mb-4 mt-2 outline-gray-300">
                <input
                  id="description"
                  name="description"
                  type="text"
                  placeholder="Task description"
                  className="border rounded-md"
                  autoComplete="off"
                  onChange={(e) => setDescription(e.target.value)}
                />
              </div>
              <div className="sm:col-span-3">
                <label
                  htmlFor="status"
                  className="block font-medium text-xl text-gray-900"
                >
                  Status
                </label>
                <div className="mb-4 mt-2 grid grid-cols-1">
                  <select
                    id="status"
                    name="status"
                    className="col-start-1 row-start-1 w-full appearance-none rounded-md bg-white py-1.5 pr-8 pl-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                    required
                  >
                    <option value="Todo">Todo</option>
                    <option value="Complete">Complete</option>
                    <option value="In progress">In progress</option>
                  </select>
                  <ChevronDownIcon
                    aria-hidden="true"
                    className="pointer-events-none col-start-1 row-start-1 mr-2 size-5 self-center justify-self-end text-gray-500 sm:size-4"
                  />
                </div>
              </div>
              <label htmlFor="dueby" className="text-xl">
                Due by
              </label>
              <div>
                <div>
                  <input
                    id="dueby"
                    name="dueby"
                    type="datetime-local"
                    value={dueBy}
                    placeholder="Due date & time"
                    className="border mt-2 mb-4 rounded-md"
                    autoComplete="off"
                    required
                    onChange={(e) => setDueBy(e.target.value)}
                  />
                </div>
              </div>
            </div>
          </div>
          <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
            <button
              type="submit"
              onClick={handleSubmit}
              className="inline-flex w-full justify-center rounded-md bg-[#87a5ff] px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-red-500 sm:ml-3 sm:w-auto"
            >
              Create
            </button>
            <button
              type="button"
              data-autofocus
              onClick={() => setOpenAction(false)}
              className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs ring-1 ring-gray-300 ring-inset hover:bg-gray-50 sm:mt-0 sm:w-auto"
            >
              Cancel
            </button>
          </div>
        </form>
      </DialogPanel>
    </Dialog>
  );
}
