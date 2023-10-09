import { createRouter, createWebHashHistory } from 'vue-router';
import warehouseComponent from "@/components/WarehouseComponent";
import dashboardComponent from "@/components/DashboardComponent.vue";
import userComponent from "@/components/userComponent.vue";
import productComponent from "@/components/ProductComponent.vue";

const routes = [
    {
        path: '/warehouse',
        name: 'Warehouse',
        component: warehouseComponent
    },
    {
        path: '/product',
        name: 'Product',
        component: productComponent
    },
    {
        path: '/user',
        name: 'User',
        component: userComponent
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: dashboardComponent
    }
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})