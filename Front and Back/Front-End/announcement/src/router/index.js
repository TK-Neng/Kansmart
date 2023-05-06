import { createRouter, createWebHistory } from "vue-router";
import Main from "../views/Main.vue";
import Detail from "../views/Detail.vue";
import Add from "../views/Add.vue";
import Edit from "../views/Edit.vue";

const history = createWebHistory(import.meta.env.BASE_URL);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/detail/:id",
    name: "Detail",
    component: Detail,
  },
  {
    path: "/add",
    name: "Add",
    component: Add,
  },
  {
    path: "/edit/:id",
    name: "Edit",
    component: Edit,
  }
];

const router = createRouter({ history, routes });

export default router;
