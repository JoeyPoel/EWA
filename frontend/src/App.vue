<template>
  <NavbarComponent/>
  <router-view/>
</template>

<script>

import NavbarComponent from "@/components/NavbarComponent";

export default {
  name: 'App',
  components: {
    NavbarComponent,
  }
}
</script>