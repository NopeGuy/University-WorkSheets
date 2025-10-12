using System.ComponentModel.DataAnnotations;

namespace Noitcua.Models
{
    public class RegisterModel
    {
        [Required]
        [MinLength(3)]
        [MaxLength(20)]
        public string Handle { get; set; }

        //[Required]
        

        [Required]
        [EmailAddress]
        [MaxLength(50)]
        public string Email { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [MinLength(8)]
        [MaxLength(50)]
        public string Password { get; set; }

        [DataType(DataType.Password)]
        [Compare("Password", ErrorMessage = "The password and confirmation password do not match.")]
        public string ConfirmPassword { get; set; }
    }
}
