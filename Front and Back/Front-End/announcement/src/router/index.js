import { createRouter, createWebHistory } from "vue-router";
import Main from "../views/Main.vue";
import Detail from "../views/Detail.vue";

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
];

const router = createRouter({ history, routes });

export default router;
