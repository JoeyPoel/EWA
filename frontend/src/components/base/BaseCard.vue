<template>
  <v-card :class="classes" class="pa-3" v-bind="$attrs">
    <div class="d-flex grow flex-wrap">
      <v-avatar v-if="avatar" class="mx-auto avatar elevation-6" color="grey" size="128">
        <v-img :src="avatar"/>
      </v-avatar>
      <v-sheet v-else :class="{'pa-7': !$slots.image}" :color="color" :max-height="icon ? 90 : undefined"
               :width="icon ? 'auto' : '100%'" class="text-start heading mb-n6" dark elevation="6">
        <slot v-if="$slots.heading" name="heading"/>
        <slot v-else-if="$slots.image" name="image"/>
        <div v-else-if="title && !icon" class="display-1 font-weight-light" v-text="title"/>
        <v-icon v-else-if="icon" size="32" v-text="icon"/>
        <div v-if="text" class="headline font-weight-thin" v-text="text"/>
      </v-sheet>
      <div v-if="$slots['after-heading']" class="ml-6">
        <slot name="after-heading"/>
      </div>
      <div v-else-if="icon && title" class="ml-4">
        <div class="card-title font-weight-light" v-text="title"/>
      </div>
    </div>
    <slot/>
    <template v-if="$slots.actions">
      <v-divider class="mt-2"/>
      <v-card-actions class="pb-0">
        <slot name="actions"/>
      </v-card-actions>
    </template>
  </v-card>
</template>

<script>
export default {
  name: 'Card',

  props: {
    avatar: {
      type: String,
      default: '',
    },
    color: {
      type: String,
      default: 'success',
    },
    icon: {
      type: String,
      default: undefined,
    },
    image: {
      type: Boolean,
      default: false,
    },
    text: {
      type: String,
      default: '',
    },
    title: {
      type: String,
      default: '',
    },
  },

  computed: {
    classes() {
      return (this.avatar ? 'avatar ' : '') + (this.hasHeading ? 'heading' : '') ;
    },
    hasHeading() {
      return Boolean(this.$slots.heading || this.title || this.icon)
    }
  },
}
</script>

<style>
.avatar {
  position: relative;
  top: -64px;
  margin-bottom: -32px;
}
.heading {
  position: relative;
  top: -40px;
  transition: 0.3s ease;
  z-index: 1;
}
</style>
