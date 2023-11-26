// Vue
import {createApp} from 'vue';
import App from './App.vue';
// Router
import {router} from "@/router";
// Bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.js';
// Vuetify
import 'vuetify/styles'
import vuetify from './plugins/vuetify'

createApp(App)
    .use(router)
    .use(vuetify)
    .mount('#app')
