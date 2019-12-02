<template>
    <fade-transition>
        <div :class="[`alert-${type}`, {'alert-dismissible': dismissible}]" class="alert" role="alert" v-if="visible">
            <slot v-if="!dismissible">
                <span class="alert-inner--icon" v-if="icon">
                    <i :class="icon"></i>
                </span>
                <span class="alert-inner--text" v-if="$slots.text">
                    <slot name="text"></slot>
                </span>
            </slot>
            <template v-else>
                <slot>
                    <span class="alert-inner--icon" v-if="icon">
                     <i :class="icon"></i>
                    </span>
                    <span class="alert-inner--text" v-if="$slots.text">
                    <slot name="text"></slot>
                </span>
                </slot>
                <slot name="dismiss-icon">
                    <button @click="dismissAlert" aria-label="Close" class="close" data-dismiss="alert" type="button">
                        <span aria-hidden="true">×</span>
                    </button>
                </slot>
            </template>
        </div>
    </fade-transition>
</template>
<script>
    import {FadeTransition} from "vue2-transitions";

    export default {
        name: "base-alert",
        components: {
            FadeTransition
        },
        props: {
            type: {
                type: String,
                default: "default",
                description: "Alert type"
            },
            icon: {
                type: String,
                default: "",
                description: "Alert icon. Will be overwritten by default slot"
            },
            dismissible: {
                type: Boolean,
                default: false,
                description: "Whether alert is closes when clicking"
            }
        },
        data() {
            return {
                visible: true
            };
        },
        methods: {
            dismissAlert() {
                this.visible = false;
            }
        }
    };
</script>
