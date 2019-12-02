<template>
    <div :aria-expanded="active"
         :class="{'active show': active}"
         :id="id || label"
         class="tab-pane fade"
         v-show="active">
        <slot></slot>
    </div>
</template>
<script>
    export default {
        name: "tab-pane",
        props: ["label", "id", "title"],
        inject: ["addTab", "removeTab"],
        data() {
            return {
                active: false
            };
        },
        mounted() {
            this.addTab(this);
        },
        destroyed() {
            if (this.$el && this.$el.parentNode) {
                this.$el.parentNode.removeChild(this.$el);
            }
            this.removeTab(this);
        }
    };
</script>
<style>
</style>
