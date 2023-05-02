const getData = async () => {
  try {
    const res = await fetch("http://localhost:8080/api/pl1/announcements");
    if(res.status === 404) {
      const data = res.status;
      return data;
    }
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
    if(res.status === 500) {
      const data = res.status;
      return data;
    }
    if(res.status === 404) {
      const data = res.status;
      return data;
    }
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
