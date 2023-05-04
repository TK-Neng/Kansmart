import { createRouter, createWebHistory } from "vue-router";
import Main from "../views/Main.vue";
import Detail from "../views/Detail.vue";
import Add from "../views/Add.vue";

const history = createWebHistory();

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/detail",
    name: "Detail",
    component: Detail,
  },
  {
    path: "/add",
    name: "Add",
    component: Add,
  },
];

const router = createRouter({ history, routes });

export default router;
