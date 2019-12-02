<template>
    <div :class="{[`bg-${type}`]: type}" class="header">
        <div class="container-fluid">
            <div class="header-body">
                <slot></slot>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'base-header',
        props: {
            type: {
                type: String,
                default: 'primary',
                description: 'Header background type'
            }
        }
    }
</script>
<style>
</style>
