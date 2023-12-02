export class ValidationRules {
    static date

    static requiredRule = (value) => !!value || "Required.";

    static minLengthRule = (minLength) => (value) => (value && value.length >= minLength) ||
        `Must be at least ${minLength} characters long.`;

    static maxLengthRule = (maxLength) => (value) => (value && value.length <= maxLength) ||
        `Must be at most ${maxLength} characters long.`;

    static emailRule = (value) => (value && value.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) || "Invalid email.";

    static numberRule = (value) => (value && !isNaN(value)) || "Must be a number.";

    static minValueRule = (minValue) => (value) => (value && value >= minValue) || `Must be at least ${minValue}.`;

    static maxValueRule = (maxValue) => (value) => (value && value <= maxValue) || `Must be at most ${maxValue}.`;

    static regexRule = (regex) => (value) => (value && value.match(regex)) || "Invalid input.";

    static passwordRule = (value) => (value && value.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/)) ||
        "Password must be at least 8 characters long, contain at least one lowercase letter," +
        " one uppercase letter and one number.";

    static passwordConfirmationRule = (password) => (value) => (value && value === password) ||
        "Passwords do not match.";

    static dateRule = (value) => (value && value.match(/^\d{4}-\d{2}-\d{2}$/)) || "Invalid date.";

    static dateBeforeRule = (date) => (value) => (value && value < date) || `Date must be before ${date}.`;

    static dateAfterRule = (date) => (value) => (value && value > date) || `Date must be after ${date}.`;

    static dateBeforeOrEqualRule = (date) => (value) => (value && value <= date) || `Date must be before or equal to 
    ${date}.`;

    static dateAfterOrEqualRule = (date) => (value) => (value && value >= date) || `Date must be after or equal to 
    ${date}.`;
}