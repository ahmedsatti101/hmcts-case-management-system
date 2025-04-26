import Page from "@/app/task/[id]/page";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
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

  it("Should render 'Delete task' button on the screen", async () => {
    const button = await screen.findByRole("button", { name: "Delete task" });

    expect(button).toBeInTheDocument();
  });

  it("Pressing on 'Delete task' button should trigger opening modal", async () => {
    const button = await screen.findByRole("button", { name: "Delete task" });
    fireEvent.click(button);

    await screen.findByText("Are you sure you want to delete this task? This action is cannot be undone.")
  });

  it("Pressing 'Cancel' after modal is opened when task needs to be deleted should close it", async () => {
    const button = await screen.findByRole("button", { name: "Delete task" });
    fireEvent.click(button);

    const cancelButton = await screen.findByRole("button", { name: "Cancel" });
    fireEvent.click(cancelButton);

    await waitFor(() => {
      const text = screen.queryByText("Are you sure you want to delete this task? This action is cannot be undone.");
      expect(text).not.toBeInTheDocument();
    }); 
  });
});
