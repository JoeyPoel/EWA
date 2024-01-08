import {createRouter, createWebHashHistory} from 'vue-router';
import {jwtDecode} from "jwt-decode";


const routes = [
    {
        path: '/inventories',
        name: 'Inventories',
        component: () => import('@/views/admin/inventories/InventoriesComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/warehouses',
        name: 'Warehouses',
        component: () => import('@/views/admin/warehouses/WarehousesComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/products',
        name: 'Products',
        component: () => import('@/views/admin/products/ProductsComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/products2',
        name: 'Products2',
        component: () => import('@/views/admin/products/ProductsV2.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/projects',
        name: 'Projects',
        component: () => import('@/views/admin/projects/ProjectsComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['ADMIN'],
        }
    },
    {
        path: '/projects2',
        name: 'Projects2',
        component: () => import('@/views/admin/projects/ProjectsV2.vue'),
        meta: {
            requiresAuth: true,
            roles: ['ADMIN'],
        }
    },
    {
        path: '/users',
        name: 'Users',
        component: () => import('@/views/admin/users/UsersComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['ADMIN'],
        }
    },
    {
        path: '/users2',
        name: 'Users2',
        component: () => import('@/views/admin/users/UsersV2.vue'),
        meta: {
            requiresAuth: true,
            roles: ['ADMIN'],
        }
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/admin/dashboard/DashboardComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/teams',
        name: 'Teams',
        component: () => import('@/views/admin/teams/TeamsComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/teams2',
        name: 'Teams2',
        component: () => import('@/views/admin/teams/TeamsV2.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
        },
    },
    {
        path: '/log-in',
        name: 'Login',
        component: () => import('@/components/user/LoginComponent.vue'),
        meta: {
            hideNavbar: true
        },
    },
    {
        path: '/', redirect: '/log-in',
    },
    {
        path: '/pass-forgot',
        name: 'PassForgot',
        component: () => import('@/components/user/PassForgotComponent.vue'),
        meta: {
            hideNavbar: true
        },
    },
    {
        path: '/pass-reset:token',
        name: 'PassReset',
        component: () => import('@/components/user/ResetPassComponent.vue'),
        meta: {
            hideNavbar: true
        },
    },
    {
        path: '/team-projects',
        name: 'Team Projects',
        component: () => import('@/views/user/projects/ProjectsComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER'],
        }
    },
    {
        path: '/error',
        name: 'Error',
        component: () => import('@/components/ErrorComponent.vue'),
        meta: {
            requiresAuth: true,
            roles: ['USER', 'ADMIN'],
            noPadding: true
        },
    },
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem('token');
    console.log('isAuthenticatedRouter:', isAuthenticated);


    if (to.matched.some((record) => record.meta.requiresAuth)) {
        // Check if the route requires authentication
        if (!isAuthenticated) {
            // User is not authenticated, redirect to login
            next({ name: 'Login' });
        } else {
            // User is authenticated, decode the JWT token
            const decodedToken = jwtDecode(isAuthenticated);

            // Check if the user's role matches any of the required roles for the route
            if (checkRoleAccess(to, decodedToken.role)) {
                // User has the required role for the route, proceed
                next();
            } else {
                // User does not have the required role, redirect to unauthorized page or another suitable page
                next({ name: 'Unauthorized' }); // You need to define an 'Unauthorized' route
            }
        }
    } else {
        // Route does not require authentication
        if (!isAuthenticated) {
            next();
        } else {
            // User is authenticated, redirect to dashboard or another suitable page
            next({ name: 'Dashboard' });
        }
    }
});

function checkRoleAccess(route, userRole) {
    // Check if the route has a 'roles' property in its meta field
    if (route.meta.roles) {
        // Check if the user's role matches any of the required roles for the route
        return route.meta.roles.includes(userRole);
    }
    // If the route does not have a 'roles' property, access is allowed
    return true;
}

