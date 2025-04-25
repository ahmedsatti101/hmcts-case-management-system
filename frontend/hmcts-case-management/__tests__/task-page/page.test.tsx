import Page from "@/app/task/[id]/page";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { Task } from "@/app/taskModel";
import axios from "axios";

jest.mock("axios");

jest.mock("next/navigation", () => ({
  useParams: jest.fn(() => ({ id: "1" })),
}));

const task: Task = {
  id: 1,
  title: "Test task",
  description: "Test task description",
  status: "Done",
  due: "2025-05-01T09:00:00",
};

beforeEach(() => {
  (axios.get as jest.Mock).mockResolvedValue({ data: task });

  render(<Page />);
});

describe("Single task page", () => {
  it("Task title should be on the screen", async () => {
    const title = await screen.findByTestId("single-task-title");

    expect(title).toBeInTheDocument();
    expect(title).toHaveTextContent(task.title);
  });

  it("Task description should be on the screen", async () => {
    const description = await screen.findByTestId("single-task-description");

    expect(description).toBeInTheDocument();
    expect(description).not.toBeNull();
  });

  it("Task status should be on the screen", async () => {
    const status = await screen.findByTestId("single-task-status");

    expect(status).toBeInTheDocument();
    expect(status).toHaveTextContent(task.status);
  });

  it("Task duedate should be on the screen", async () => {
    const dueDate = await screen.findByTestId("single-task-duedate");

    expect(dueDate).toBeInTheDocument();
    expect(dueDate).not.toBeNull();
  });

  it("Should render 'Back to home' button", async () => {
    const button = await screen.findByTestId("back-home-button");

    expect(button).toBeInTheDocument();
    expect(button).toHaveTextContent("Back to home");
  });

  it("Should render loading text while requested task is being fetched", () => {
    render(<Page />);

    expect(screen.getByText("Loading...")).toBeInTheDocument();
  });
});
