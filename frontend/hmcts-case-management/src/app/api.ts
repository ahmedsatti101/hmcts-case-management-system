import axios from "axios";

const url = "http://localhost:8080/api/task";

export const getAllTasks = async () => {
  try {
    const response = await axios
      .get(url);
    return response.data;
  } catch (err) {
    throw err;
  }
}

export const getSingleTask = async (id: string) => {
  try {
    const response = await axios
      .get(url + `/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const deleteTask = async (id: string) => {
  try {
    const response = await axios
      .delete(url + `/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
