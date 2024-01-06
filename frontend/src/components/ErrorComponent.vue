<script>
export default {
  name: "ErrorComponent"
}
</script>

<template>
  <main class="d-flex align-items-center min-vh-100 py-3 py-md-0 bg-custom w-100">
    <div class="container  justify-content-center">
      <div class="col-7 mx-auto bg-transparent">
        <div class="card-body">
          <div class="brand-wrapper text-center">
            <img class="logo2" src="../assets/logo.png" alt="logo">
            <h1 class="pt-5">Sorry, something went wrong!</h1>
            <h5 class="pt-1 pb-3">Contact an administrator for support.</h5>
            <router-link to="/dashboard" class="btn button btn-primary">Back to dashboard</router-link>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
@import "@/assets/styles/userFormStyles.css";
</style>