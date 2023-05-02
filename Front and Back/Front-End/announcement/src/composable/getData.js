const getData = async () => {
  try {
    const res = await fetch("http://localhost:8080/api/pl1/announcements");
    if (res.ok) {
      const data = res.json();
      return data;
    } else {
      throw new error("Error, can't get data");
    }
  } catch (error) {
    console.log(error);
  }
};

const getDataById = async (id) => {
  try {
    const res = await fetch(`http://localhost:8080/api/pl1/announcements/${id}`);
    if (res.ok) {
      const data = res.json();
      return data;
    } else {
      throw new error("Error, can't get data");
    }
  } catch (error) {
    console.log(error);
  }
};

export { getData, getDataById };
