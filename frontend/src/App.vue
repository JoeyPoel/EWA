<template>
  <div class="d-flex">
    <NavbarComponent/>
    <div class="flex-grow-1">
      <router-view/>
    </div>
  </div>
</template>

<script>

import NavbarComponent from "@/components/NavbarComponent";

export default {
  name: 'App',
  components: {
    NavbarComponent
  }
}
</script>