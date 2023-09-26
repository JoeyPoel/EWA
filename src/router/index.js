import { createRouter, createWebHashHistory } from 'vue-router';
import warehouseComponent from "@/components/WarehouseComponent";
const routes = [
    {
        path: '/warehouse',
        name: 'Warehouse',
        component: warehouseComponent
    },
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})