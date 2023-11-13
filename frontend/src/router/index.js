import { createRouter, createWebHashHistory } from 'vue-router';
import warehouseInventoryComponent from "@/components/WarehouseInventoryComponent.vue";
import WarehouseDetailComponent from "@/components/WarehouseDetailComponent.vue";
import warehouseOverviewComponent from "@/components/WarehouseOverviewComponent.vue";
import AdminProductsComponent from "@/components/AdminProductsComponent.vue";
import dashboardComponent from "@/components/DashboardComponent.vue";
import userComponent from "@/components/userComponent.vue";
import productComponent from "@/components/ProductComponent.vue";
import projectComponent from "@/components/ProjectComponent.vue";
import ProductDetail from "@/components/products/ProductDetail.vue";

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
        path: '/product',
        name: 'Product',
        component: productComponent,
        children: [
            {
                path: ':id',
                name: 'ProductDetail',
                component: ProductDetail,
                props: true
            }
        ]
    },
    {
        path: '/project',
        name: 'Project',
        component: projectComponent
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
    },
    {
        path: '/admin/products',
        name: 'AdminProducts',
        component: AdminProductsComponent
    }
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})