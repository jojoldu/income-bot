<template>
    <label class="custom-toggle">
        <input type="checkbox"
               v-bind="$attrs"
               v-model="model"
               v-on="$listeners">
        <span class="custom-toggle-slider rounded-circle"></span>
    </label>
</template>
<script>
    export default {
        name: "base-switch",
        inheritAttrs: false,
        props: {
            value: {
                type: Boolean,
                default: false,
                description: "Switch value"
            }
        },
        computed: {
            model: {
                get() {
                    return this.value;
                },
                set(value) {
                    this.$emit("input", value);
                }
            }
        }
    };
</script>
<style>
</style>
