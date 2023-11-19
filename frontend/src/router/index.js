import { createRouter, createWebHashHistory } from 'vue-router';
import warehouseInventoryComponent from "@/components/WarehouseInventoryComponent.vue";
// import WarehouseDetailComponent from "@/components/warehouses/WarehouseDetailComponent.vue";
import warehouseOverviewComponent from "@/components/warehouses/WarehouseOverviewComponent.vue";
import AdminProductsComponent from "@/components/AdminProductsComponent.vue";
import dashboardComponent from "@/components/DashboardComponent.vue";
import userComponent from "@/components/userComponent.vue";
import productComponent from "@/components/products/ProductComponent.vue";
// import projectComponent from "@/components/projects/ProjectComponent.vue";
import ProductDetail from "@/components/products/ProductDetail.vue";
import ProductAdd from "@/components/products/ProductAdd.vue";
import TeamComponent from "@/components/teamComponent";
import LoginComponent from "@/components/LoginComponent.vue";
import WarehouseDetailModalComponent from "@/components/warehouses/WarehouseDetailModalComponent.vue";
import WarehouseAddModalComponent from "@/components/warehouses/WarehouseAddModalComponent.vue";
import projectListComponent from "@/components/projects/ProjectListComponent.vue";

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
                    // {
                    //     path: ':id',
                    //     name: 'WarehouseDetailComponent',
                    //     component: WarehouseDetailComponent
                    // },
                    {
                        path: ':id',
                        name: 'WarehouseDetailModalComponent',
                        component: WarehouseDetailModalComponent
                    },
                    {
                        path: 'add',
                        name: 'WarehouseAddComponent',
                        component: WarehouseAddModalComponent
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
            },
            {
                path: 'add',
                name: 'ProductAdd',
                component: ProductAdd,
            }
        ]
    },
    {
        path: '/project',
        name: 'Project',
        component: projectListComponent
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
    },
    {
        path: '/teams',
        name: 'Teams',
        component: TeamComponent
    },
    {
        path: '/log-in',
        name: 'Login',
        component: LoginComponent
    },
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})