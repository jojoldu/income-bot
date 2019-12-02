<template>
    <button :aria-controls="target" :aria-expanded="expanded"
            :data-target="`#${target}`"
            @click="handleClick"
            aria-label="Toggle navigation"
            class="navbar-toggler"
            data-toggle="collapse"
            type="button">
        <span></span>
        <span></span>
    </button>
</template>
<script>
    export default {
        name: "close-button",
        props: {
            target: {
                type: [String, Number],
                description: "Close button target element"
            },
            expanded: {
                type: Boolean,
                description: "Whether button is expanded (aria-expanded attribute)"
            }
        },
        methods: {
            handleClick(evt) {
                this.$emit("click", evt);
            }
        }
    };
</script>
<style>
</style>
