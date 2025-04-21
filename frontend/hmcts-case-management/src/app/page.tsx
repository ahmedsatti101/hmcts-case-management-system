"use client";

export default function Home() {
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
      <div className="border rounded-[10px] mt-5 relative m-auto max-w-150 bg-[#fff9f9]">
        <p className="text-2xl bg-[#7a93de] font-serif">Title</p>
        <p className="text-xl mt-1 font-serif">This will be task description</p>
        <br />
        <br />
        <br />
        <div className="absolute bottom-0 text-xl font-serif">Status: Todo</div>
      </div>
    </>
  );
}
