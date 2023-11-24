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
import {createVuetify} from 'vuetify'
import { aliases, mdi } from 'vuetify/iconsets/mdi-svg'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import {mdiCancel, mdiContentSaveOutline, mdiTrashCanOutline , mdiInformationOutline, mdiPencilOutline} from '@mdi/js'

const vuetify = createVuetify({
    components,
    directives,
    theme: {
        defaultTheme: 'light',
    },
    icons: {
        defaultSet: 'mdi',
        aliases: {
            ...aliases,
            edit: mdiPencilOutline,
            delete: mdiTrashCanOutline ,
            save: mdiContentSaveOutline,
            cancel: mdiCancel,
            info: mdiInformationOutline,
        },
        sets: {
            mdi,
        },
    },
})

createApp(App)
    .use(router)
    .use(vuetify)
    .mount('#app')
