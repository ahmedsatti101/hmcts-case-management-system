import axios from "axios";

export const getAllTasks = async () => {
  try {
    const response = await axios
      .get("http://localhost:8080/api/task");
    return response.data;
  } catch (err) {
    throw err;
  }
}

export const getSingleTask = async (id: string) => {
  try {
    const response = await axios
      .get(`http://localhost:8080/api/task/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
