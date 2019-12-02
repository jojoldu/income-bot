<template>
    <div :class="[
       {'input-group': hasIcon},
       {'has-danger': error},
       {'focused': focused},
       {'has-label': label || $slots.label},
       {'has-success': valid === true},
       {'has-danger': valid === false}
       ]"
         class="form-group">
        <slot name="label">
            <label :class="labelClasses" class="form-control-label" v-if="label">
                {{label}}
                <span v-if="required">*</span>
            </label>
        </slot>


        <div class="input-group-prepend" v-if="addonLeftIcon || $slots.addonLeft">
        <span class="input-group-text">
          <slot name="addonLeft">
            <i :class="addonLeftIcon"></i>
          </slot>
        </span>
        </div>
        <slot v-bind="slotData">
            <input
                    :class="[
                     {'is-valid': valid === true},
                     {'is-invalid': valid === false}, inputClasses]"
                    :value="value"
                    aria-describedby="addon-right addon-left"
                    class="form-control"
                    v-bind="$attrs"
                    v-on="listeners">
        </slot>
        <div class="input-group-append" v-if="addonRightIcon || $slots.addonRight">
          <span class="input-group-text">
              <slot name="addonRight">
                  <i :class="addonRightIcon"></i>
              </slot>
          </span>
        </div>
        <slot name="infoBlock"></slot>
        <slot name="helpBlock">
            <div :class="{'mt-2': hasIcon}" class="text-danger invalid-feedback" style="display: block;" v-if="error">
                {{ error }}
            </div>
        </slot>
    </div>
</template>
<script>
    export default {
        inheritAttrs: false,
        name: "base-input",
        props: {
            required: {
                type: Boolean,
                description: "Whether input is required (adds an asterix *)"
            },
            valid: {
                type: Boolean,
                description: "Whether is valid",
                default: undefined
            },
            label: {
                type: String,
                description: "Input label (text before input)"
            },
            error: {
                type: String,
                description: "Input error (below input)"
            },
            labelClasses: {
                type: String,
                description: "Input label css classes"
            },
            inputClasses: {
                type: String,
                description: "Input css classes"
            },
            value: {
                type: [String, Number],
                description: "Input value"
            },
            addonRightIcon: {
                type: String,
                description: "Addon right icon"
            },
            addonLeftIcon: {
                type: String,
                description: "Addont left icon"
            }
        },
        data() {
            return {
                focused: false
            };
        },
        computed: {
            listeners() {
                return {
                    ...this.$listeners,
                    input: this.updateValue,
                    focus: this.onFocus,
                    blur: this.onBlur
                };
            },
            slotData() {
                return {
                    focused: this.focused,
                    ...this.listeners
                };
            },
            hasIcon() {
                const {addonRight, addonLeft} = this.$slots;
                return (
                    addonRight !== undefined ||
                    addonLeft !== undefined ||
                    this.addonRightIcon !== undefined ||
                    this.addonLeftIcon !== undefined
                );
            }
        },
        methods: {
            updateValue(evt) {
                let value = evt.target.value;
                this.$emit("input", value);
            },
            onFocus(value) {
                this.focused = true;
                this.$emit("focus", value);
            },
            onBlur(value) {
                this.focused = false;
                this.$emit("blur", value);
            }
        }
    };
</script>
<style>
</style>
