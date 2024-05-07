import { lazy } from "react";
import { Navigate } from "react-router-dom";

import AuthGuard from "./auth/AuthGuard";
import { authRoles } from "./auth/authRoles";

import Loadable from "./components/Loadable";
import MatxLayout from "./components/MatxLayout/MatxLayout";

import sessionRoutes from "./views/sessions/session-routes";
import materialRoutes from "app/views/material-kit/MaterialRoutes";
import { element } from "prop-types";

// E-CHART PAGE
const AppEchart = Loadable(lazy(() => import("app/views/charts/echarts/AppEchart")));
// DASHBOARD PAGE
const Analytics = Loadable(lazy(() => import("app/views/dashboard/Analytics")));
const Empresa = Loadable(lazy(() => import("app/views/dashboard/Empresa")));
const NewEmpresa = Loadable(lazy(() => import("app/views/dashboard/NewEmpresa")));
const Producto = Loadable(lazy(() => import("app/views/dashboard/Producto")));
const NewProducto = Loadable(lazy(() => import("app/views/dashboard/NewProducto")));
const Inventario = Loadable(lazy(() => import("app/views/dashboard/Inventario")));

const routes = [
  {
    path: "/",
    element: <Navigate to="dashboard/default" />
  },
  {
    element: (
      <AuthGuard>
        <MatxLayout />
      </AuthGuard>
    ),
    children: [
      ...materialRoutes,
      // dashboard route
      {
        path: "/dashboard/default",
        element: <Analytics />,
        auth: authRoles.admin
      },
      // dashboard empresa
      {
        path: "/dashboard/empresa",
        element: <Empresa />,
        auth: authRoles.admin
      },
      // dashboard nueva empresa
      {
        path : "/empresa/NewEmpresa",
        element: <NewEmpresa />,
        auth: authRoles.admin
      },
      // dashboard listado de productos
      {
        path: "/dashboard/producto",
        element: <Producto />,
        auth: authRoles.admin
      },
      // dashboard nuevo producto
      {
        path: "/producto/NewProducto",
        element: <NewProducto />,
        auth: authRoles.admin
      },
      // dashboard inventario
      {
        path: "/dashboard/inventario",
        element: <Inventario />,
        auth: authRoles.admin
      },
      // e-chart route
      {
        path: "/charts/echarts",
        element: <AppEchart />,
        auth: authRoles.editor
      }
    ]
  },

  // session pages route
  ...sessionRoutes
];

export default routes;
