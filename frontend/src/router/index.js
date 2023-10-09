import { createRouter, createWebHashHistory } from 'vue-router';
import warehouseInventoryComponent from "@/components/WarehouseInventoryComponent.vue";
import WarehouseDetailComponent from "@/components/WarehouseDetailComponent.vue";
import warehouseOverviewComponent from "@/components/WarehouseOverviewComponent.vue";

import dashboardComponent from "@/components/DashboardComponent.vue";
import userComponent from "@/components/userComponent.vue";

const routes = [
    {
        path: '/warehouse',
        name: 'Warehouse',
        children: [
            {
                path: 'inventory',
                name: 'WarehouseComponent',
                component: warehouseInventoryComponent,
                // TODO: add navigation by id
            },
            {
                path: 'overview',
                name: 'WarehouseOverviewComponent',
                component: warehouseOverviewComponent,
                children: [
                    {
                        path: ':id',
                        name: 'WarehouseDetailComponent',
                        component: WarehouseDetailComponent
                    }
                ]
            }
        ]
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