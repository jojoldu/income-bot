<template>
    <div :class="{'disabled': disabled, 'form-check-inline': inline}"
         class="custom-control custom-checkbox">
        <input :disabled="disabled"
               :id="cbId"
               class="custom-control-input"
               type="checkbox"
               v-model="model"/>
        <label :for="cbId" class="custom-control-label">
            <slot>
                <span v-if="inline">&nbsp;</span>
            </slot>
        </label>
    </div>
</template>
<script>
    import {randomString} from "./stringUtils";

    export default {
        name: "base-checkbox",
        model: {
            prop: "checked"
        },
        props: {
            checked: {
                type: [Array, Boolean],
                description: "Whether checkbox is checked"
            },
            disabled: {
                type: Boolean,
                description: "Whether checkbox is disabled"
            },
            inline: {
                type: Boolean,
                description: "Whether checkbox is inline"
            }
        },
        data() {
            return {
                cbId: "",
                touched: false
            };
        },
        computed: {
            model: {
                get() {
                    return this.checked;
                },
                set(check) {
                    if (!this.touched) {
                        this.touched = true;
                    }
                    this.$emit("input", check);
                }
            }
        },
        mounted() {
            this.cbId = randomString()
        }
    };
</script>
